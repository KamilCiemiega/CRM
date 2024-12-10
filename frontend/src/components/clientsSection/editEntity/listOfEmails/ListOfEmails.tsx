import { Box } from "@mui/material";
import { DataGrid, GridColDef, GridRowParams, GridRowSelectionModel } from "@mui/x-data-grid";
import SearchEmails from "./SearchEmails";
import { useState } from "react";
import { FiltredMessage } from "./SearchEmails";

const ListOfEmails = () => {
    const [selectedRows, setSelectedRows] = useState<GridRowSelectionModel>([]);
    const [filteredRows, setFilteredRows] = useState<FiltredMessage[]>([]);

    const columns: GridColDef[] = [
        { field: 'subject', headerName: 'Subject', flex: 2 },
        { field: 'time', headerName: 'Time', flex: 1 },
        { field: 'size', headerName: 'Size', flex: 1 },
    ];

    const handleSelectionChange = (newSelection: GridRowSelectionModel) => {
        setSelectedRows(newSelection);
    };

    const handleRowClick = (params: GridRowParams) => {
        
    };


     return (
        <Box>
            <SearchEmails onSearch={setFilteredRows}/>
            <DataGrid
            rows={filteredRows}
            columns={columns}
            rowSelectionModel={selectedRows}
            checkboxSelection
            onRowSelectionModelChange={handleSelectionChange}
            onRowClick={handleRowClick}
        />
        </Box>
     );
}

export default ListOfEmails;