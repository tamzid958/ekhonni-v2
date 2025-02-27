import React, { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { X } from 'lucide-react';
import { Select } from '@/components/ui/select';

interface RoleSelectorProps {
  initialRoles: string[];
  onChange: (updatedRoles: string[]) => void;
  availableRoles: string[];
}

export default const RoleSelector: React.FC<RoleSelectorProps> = ({ initialRoles, onChange, availableRoles }) => {
  const [selectedRoles, setSelectedRoles] = useState<string[]>(initialRoles);

  const handleRoleChange = (selectedOptions: any) => {
    const updatedRoles = selectedOptions ? selectedOptions.map((option: any) => option.value) : [];
    setSelectedRoles(updatedRoles);
    onChange(updatedRoles);
  };

  const handleRemoveRole = (role: string) => {
    const updatedRoles = selectedRoles.filter((r) => r !== role);
    setSelectedRoles(updatedRoles);
    onChange(updatedRoles);
  };
  const roleOptions = availableRoles.map((role) => ({ value: role, label: role }));

  return (
    <div className="space-y-2">
      {/* Display Selected Roles */}
      <div className="flex flex-wrap gap-2">
        {selectedRoles.length > 0 ? (
          selectedRoles.map((role, index) => (
            <Badge key={index} className="flex items-center bg-gray-200 text-gray-800">
              {role}
              <Button
                variant="link"
                className="ml-1 p-0"
                onClick={() => handleRemoveRole(role)}
              >
                <X className="h-4 w-4 text-red-500" />
              </Button>
            </Badge>
          ))
        ) : (
          <span>No roles selected</span>
        )}
      </div>
      <Select
        isMulti
        options={roleOptions}
        value={roleOptions.filter((role) => selectedRoles.includes(role.value))}
        onChange={handleRoleChange}
        className="mt-2"
        placeholder="Select roles"
      />
    </div>
  );
};
