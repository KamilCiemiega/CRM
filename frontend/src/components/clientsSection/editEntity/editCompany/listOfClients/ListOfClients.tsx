import { DataGrid, GridColDef, GridRowParams, GridRowSelectionModel } from "@mui/x-data-grid";
import { useEffect, useState } from "react";
import SearchClientsTable from "./SearchClientsTable";
import { Box, Paper } from "@mui/material";
import "../../../../../style/ListOfClients.css"
import { Client } from "../../../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import DeleteIcon from '@mui/icons-material/Delete';

type Row = {
    id: number;
    name: string;
    surName: string;
    email: string;
}

const ListOfClients = () => {
    const [selectedRows, setSelectedRows] = useState<GridRowSelectionModel>([]);
    const [filteredRows, setFilteredRows] = useState<Client[]>([]);
    const [rowsData , setRowsData] = useState<Row[]>([]);

    const columns: GridColDef[] = [
        { field: 'name', headerName: 'Name', flex: 1 },
        { field: 'surName', headerName: 'SurName', flex: 1 },
        { field: 'email', headerName: 'Email', flex: 1 },
    ];

    const handleSelectionChange = (newSelection: GridRowSelectionModel) => {
        setSelectedRows(newSelection);
    };

    const handleRowClick = (params: GridRowParams) => {
        console.log(Number(params.row.id));    
    };

    const handleDeleteClient = () => {
        if (selectedRows.length > 0) {
            const deletedRows = filteredRows.filter(client =>
                !selectedRows.includes(client.id)
            );
            setFilteredRows(deletedRows);
            setSelectedRows([]); 
        }
    };

    useEffect(() => {
        if(filteredRows){
          const rowsToDisplay = filteredRows.map(client => ({
                id: client.id, 
                name: client.name,
                surName: client.surname,
                email: client.email
            }));
            setRowsData(rowsToDisplay);
        }

    }, [filteredRows])


    return(
        <Box className="container">
        <Box className="topSectionContainer">
            <SearchClientsTable onSearch={setFilteredRows}/>
            <DeleteIcon sx={{fontSize: "35px", cursor: "pointer"}} onClick={handleDeleteClient}/>
        </Box>
        <DataGrid
        rows={rowsData}
        columns={columns}
        rowSelectionModel={selectedRows}
        checkboxSelection
        onRowSelectionModelChange={handleSelectionChange}
        onRowClick={handleRowClick}
        sx={{width: '80%'}}
        />
      </Box>
    );
}

export default ListOfClients;