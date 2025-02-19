'use client';

import React, { useState, useEffect } from 'react';
import { Dialog, DialogContent, DialogTitle, DialogFooter } from '@/components/ui/dialog';
import { Button } from '@/components/ui/button';
import { PrivilegeSelector } from './PrivilegeSelector'; // Import PrivilegeSelector component
import { axiosInstance } from '@/data/services/fetcher';

interface Privilege {
  id?: number;
  name: string;
  type: string; // Privilege type (e.g., "Account", "Bid", etc.)
}

interface PrivilegeDialogProps {
  token: string;
  isOpen: boolean;
  onClose: () => void;
  onSave: (data: Privilege[]) => void;
  existingPrivileges: Privilege[];
  groupedPrivileges: { [key: string]: Privilege[] }; // The grouped privileges data
}

export const PrivilegeDialog: React.FC<PrivilegeDialogProps> = ({
                                                                  token,
                                                                  isOpen,
                                                                  onClose,
                                                                  onSave,
                                                                  existingPrivileges = [],
                                                                  groupedPrivileges = {},
                                                                }) => {
  const [selectedType, setSelectedType] = useState<string | null>(null);
  const [filteredPrivileges, setFilteredPrivileges] = useState<Privilege[]>([]);

  const privilegeTypes = Object.keys(groupedPrivileges); // Get the privilege types (keys)

  useEffect(() => {
    if (selectedType) {
      // Filter the privileges based on the selected type
      const privileges = groupedPrivileges[selectedType] || [];
      setFilteredPrivileges(privileges);
    }
  }, [selectedType, groupedPrivileges]);
  setFilteredPrivileges( groupedPrivileges[selectedType] || []);
  const handleTypeSelection = (type: string) => {
    setSelectedType(type); // Update the selected privilege type
  };

  const handlePrivilegeSelection = (selectedPrivileges: number[]) => {
    // Update the selected privileges in the parent component
    const selectedPrivs = filteredPrivileges.filter((privilege) =>
      selectedPrivileges.includes(privilege.id || 0)
    );
    onSave(selectedPrivs); // Save selected privileges
  };

  const handleSubmit = async () => {
    try {
      // Send selected privileges to the backend
      const response = await axiosInstance.patch(
        'api/v2/role/privileges', // Assume this is the correct endpoint
        { privileges: filteredPrivileges },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      onSave(response.data); // Pass back updated data
      onClose(); // Close the dialog
    } catch (error) {
      console.log('Error saving privileges:', error);
      alert('Failed to save privileges. Please try again.');
    }
  };

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent>
        <DialogTitle>Manage Role Privileges</DialogTitle>

        {/* Select privilege type */}
        <div>
          <label className="block text-sm font-medium">Select Privilege Type</label>
          <select
            onChange={(e) => handleTypeSelection(e.target.value)}
            className="p-2 border rounded"
            value={selectedType || ''}
          >
            <option value="">-- Select a type --</option>
            {privilegeTypes.map((type) => (
              <option key={type} value={type}>
                {type}
              </option>
            ))}
          </select>
        </div>

        {/* Show privilege selector based on selected type */}
        {selectedType && (
          <form onSubmit={handleSubmit} className="space-y-4 mt-4">
            <div>
              <label className="block text-sm font-medium">Select Privileges</label>
              <PrivilegeSelector
                privileges={filteredPrivileges}
                selectedPrivileges={existingPrivileges.map((privilege) => privilege.id || 0)}
                onChange={handlePrivilegeSelection} // Pass selected privileges to parent component
              />
            </div>

            <DialogFooter>
              <Button type="button" onClick={onClose} variant="outline">
                Cancel
              </Button>
              <Button type="submit" variant="outline">
                Save Privileges
              </Button>
            </DialogFooter>
          </form>
        )}
      </DialogContent>
    </Dialog>
  );
};
