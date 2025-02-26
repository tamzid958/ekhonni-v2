
'use client';
import React from "react";
import { useState } from "react";
import { Dialog, DialogTrigger, DialogContent, DialogHeader, DialogFooter } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Trash2 } from "lucide-react";

const ConfirmDeleteDialog = ({ onConfirm }) => {
  const [open, setOpen] = useState(false);

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button variant="destructive" className="h-8 w-8 p-0 flex items-center justify-center">
          <Trash2 className="h-4 w-4" />
        </Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>Are you sure you want to remove this privilege?</DialogHeader>
        <DialogFooter>
          <Button onClick={() => setOpen(false)} variant="outline">Cancel</Button>
          <Button onClick={() => { onConfirm(); setOpen(false); }} variant="destructive">Confirm</Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default ConfirmDeleteDialog;
