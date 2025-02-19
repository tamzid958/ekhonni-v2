import React, { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { CheckCircle, X } from 'lucide-react';

interface PrivilegeSelectorProps {
  privileges: { id: number; name: string }[];
  selectedPrivileges: number[];
  onChange: (selectedPrivileges: number[]) => void;
}

export const PrivilegeSelector: React.FC<PrivilegeSelectorProps> = ({
                                                                      privileges,
                                                                      selectedPrivileges,
                                                                      onChange,
                                                                    }) => {
  const [searchTerm, setSearchTerm] = useState("");


  const handlePrivilegeToggle = (privilegeId: number) => {
    if (selectedPrivileges.includes(privilegeId)) {
      onChange(selectedPrivileges.filter((id) => id !== privilegeId)); // Remove privilege
    } else {
      onChange([...selectedPrivileges, privilegeId]); // Add privilege
    }
  };

  const handleRemovePrivilege = (privilegeId: number) => {
    onChange(selectedPrivileges.filter((id) => id !== privilegeId)); // Remove privilege
  };

  const filteredPrivileges = privileges.filter((privilege) =>
    privilege.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="h-full max-h-[300px] overflow-y-auto">
      {/* Search Input */}
      <input
        type="text"
        placeholder="Search privileges..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        className="w-full p-2 mb-2 border rounded-md"
      />

      <div className="flex flex-wrap gap-2 overflow-auto mb-2 max-h-[100px] overflow-y-auto">
        {selectedPrivileges.map((privilegeId) => {
          const privilege = privileges.find((p) => p.id === privilegeId);
          return (
            <Badge key={privilegeId} className="flex items-center bg-gray-200 text-gray-800">
              {privilege?.name}
              <Button
                variant="link"
                className="ml-1 p-0"
                onClick={() => handleRemovePrivilege(privilegeId)}
              >
                <X className="h-4 w-4 text-red-500" />
              </Button>
            </Badge>
          );
        })}
      </div>

      {/* Display available privileges */}
      <div className="space-y-2 max-h-[150px] overflow-y-auto">
        {filteredPrivileges.length > 0 ? (
          filteredPrivileges.map((privilege) => (
            <div
              key={privilege.id}
              className={`flex items-center justify-between p-2 cursor-pointer ${
                selectedPrivileges.includes(privilege.id) ? 'bg-gray-200 text-gray-500' : 'hover:bg-gray-100'
              }`}
              onClick={() => handlePrivilegeToggle(privilege.id)}
              style={{
                pointerEvents: selectedPrivileges.includes(privilege.id) ? 'none' : 'auto',
              }}
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
    </div>
  );
};
