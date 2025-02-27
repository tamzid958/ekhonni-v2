import React, { useState, useEffect } from "react";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { CheckCircle, X } from "lucide-react";
import { axiosInstance } from '@/data/services/fetcher';
import { toast } from 'sonner';

interface PrivilegeDialogProps {
  roleId: number;
  token: string;
  isOpen: boolean;
  onClose: () => void;
  groupedPrivileges: Record<string, { id: number; name: string }[]>;
  onSave: (selectedPrivileges: number[]) => void;
}

export const PrivilegeDialog: React.FC<PrivilegeDialogProps> = ({
                                                                  roleId,
                                                                  token,
                                                                  isOpen,
                                                                  onClose,
                                                                  groupedPrivileges,
                                                                  onSave
                                                                }) => {
  const [selectedType, setSelectedType] = useState<string>("");
  const [selectedPrivileges, setSelectedPrivileges] = useState<number[]>([]);
  const [searchTerm, setSearchTerm] = useState("");
  useEffect(() => {
    if (Object.keys(groupedPrivileges).length > 0) {
      setSelectedType(Object.keys(groupedPrivileges)[0]);
    }
  }, [groupedPrivileges]);

  const handleTypeChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedType(event.target.value);
  };

  const handlePrivilegeToggle = (privilegeId: number) => {
    setSelectedPrivileges((prevSelected) =>
      prevSelected.includes(privilegeId)
        ? prevSelected.filter((id) => id !== privilegeId)
        : [...prevSelected, privilegeId]
    );
  };

  const handleRemovePrivilege = (privilegeId: number) => {
    setSelectedPrivileges((prevSelected) => prevSelected.filter((id) => id !== privilegeId));
  };

  const handleSave =async () => {

    try{
        const response = await axiosInstance.post(`/api/v2/role/${roleId}/assign/privilege`,
          { privilegeIds: selectedPrivileges },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });
        if(response.status === 200)
        {
          toast.success('Privileges assigned successfully');
        }
      onSave(response.data);
      onClose();
    }catch (error)
    {
      console.log('Error saving role:', error)
      alert('Failed to assign privileges. Please try again');

    }
    onSave(selectedPrivileges);
    onClose();
  };

  const filteredPrivileges = groupedPrivileges[selectedType]?.filter((priv) =>
    priv.name.toLowerCase().includes(searchTerm.toLowerCase())
  ) || [];

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Assign Privileges</DialogTitle>
        </DialogHeader>

        <div className="mb-4">
          <label className="block text-sm font-medium text-gray-700">Select Privilege Type:</label>
          <select
            value={selectedType}
            onChange={handleTypeChange}
            className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            {Object.keys(groupedPrivileges).map((type) => (
              <option key={type} value={type}>
                {type}
              </option>
            ))}
          </select>
        </div>

        {/* Search Input */}
        <input
          type="text"
          placeholder="Search privileges..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="w-full p-2 mb-2 border rounded-md"
        />

        {/* Selected Privileges */}
        <div className="flex flex-wrap gap-2 mb-2 max-h-[100px] overflow-y-auto">
          {selectedPrivileges.map((privilegeId) => {
            const privilege = groupedPrivileges[selectedType]?.find((p) => p.id === privilegeId);
            return privilege ? (
              <Badge key={privilegeId} className="flex items-center bg-gray-200 text-gray-800">
                {privilege.name}
                <Button
                  variant="link"
                  className="ml-1 p-0"
                  onClick={() => handleRemovePrivilege(privilegeId)}
                >
                  <X className="h-4 w-4 text-red-500" />
                </Button>
              </Badge>
            ) : null;
          })}
        </div>

        {/* Available Privileges */}
        <div className="space-y-2 max-h-[150px] overflow-y-auto">
          {filteredPrivileges.length > 0 ? (
            filteredPrivileges.map((privilege) => (
              <div
                key={privilege.id}
                className={`flex items-center justify-between p-2 cursor-pointer ${
                  selectedPrivileges.includes(privilege.id) ? "bg-gray-300" : "hover:bg-gray-100"
                }`}
                onClick={() => handlePrivilegeToggle(privilege.id)}
              >
                <span>{privilege.name}</span>
                {selectedPrivileges.includes(privilege.id) && (
                  <CheckCircle className="h-4 w-4 text-green-500" />
                )}
              </div>
            ))
          ) : (
            <div className="p-2 text-gray-500">No privileges found</div>
          )}
        </div>

        <DialogFooter>
          <Button variant="outline" onClick={onClose}>
            Cancel
          </Button>
          <Button className="bg-black text-white" onClick={handleSave}>
            Save
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};
