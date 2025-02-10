import * as XLSX from  "xlsx";
import {saveAs} from "file-saver";

export const exportUserDataToCSV = (users: any[], fileName: string = "user_data") => {
  if(!users || users.length === 0){
    console.error("No user data available to export.");
    return;
  }
  const workSheet = XLSX.utils.json_to_sheet(users);
  const workBook = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(workBook, workSheet, "Users");

  const csvData = XLSX.write(workBook, {bookType: "csv", type : "array"});
  const blob = new Blob([csvData], {type: "text/csv; charset = utf-8;"});
  saveAs(blob, `${fileName}.csv`);

}