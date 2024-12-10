import { DataGrid, GridColDef, GridRowParams, GridRowSelectionModel } from "@mui/x-data-grid";
import { useEffect, useState } from "react";
import SearchClientsTable from "./SearchClientsTable";
import { Box } from "@mui/material";
import "../../../../../style/ListOfClients.css"
import { Client } from "../../../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import DeleteIcon from '@mui/icons-material/Delete';
import { clientViewAction } from "../../../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";

import { useDispatch } from "react-redux";

type Row = {
    id: number;
    name: string;
    surName: string;
    email: string;
}

const ListOfClients = () => {
    const dispatch = useDispatch();
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

    const helperFilterFunction = (clickedRowNumber?: number) => {
        return filteredRows.filter(client => 
            clickedRowNumber?
            clickedRowNumber === client.id :
            !selectedRows.includes(client.id) 
        );
    }

    const handleRowClick = (params: GridRowParams) => {
        const clientPreviewData = helperFilterFunction(Number(params.row.id));
        dispatch(clientViewAction.setClientPreviewDialogState({
            clientPreviewData,
            viewType: 'clients',
            openDialog: true
        }));
    };

    const handleDeleteClient = () => {
        if (selectedRows.length > 0 && filteredRows.length > 0) {
            const deletedRows = helperFilterFunction();
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

    }, [filteredRows, setRowsData])


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