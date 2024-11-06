import { useState } from "react";
import { DataGrid, GridColDef } from '@mui/x-data-grid';


const columns: GridColDef[] = [
    { field: 'email', headerName: 'Email', width: 350 },
    { field: 'subject', headerName: 'Subject', width: 700 },
    { field: 'sendDate', headerName: 'Time', width: 200 },
    { field: 'size', headerName: 'Size', width: 100 },
    
  ];
  
  const rows = [
    {id: 1}
  ];

  const paginationModel = { page: 0, pageSize: 5 };

const TableDataComponent = () => {
    return (
        <DataGrid
        rows={rows}
        columns={columns}
        initialState={{ pagination: { paginationModel } }}
        pageSizeOptions={[5, 10]}
        checkboxSelection
        sx={{ border: 0 }}
      />
    );
}

export default TableDataComponent;