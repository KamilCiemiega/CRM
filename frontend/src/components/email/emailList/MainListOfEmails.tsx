import { useSelector, useDispatch } from "react-redux";
import { useEffect, useState } from "react";
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { Paper } from '@mui/material';
import { fetchAllMessages } from "../../store/thunks/fetchAllMessages";
import { AppDispatch, RootState } from "../../store";


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

const MainListOfEmails = () => {
    const dispatch = useDispatch<AppDispatch>();
    const listOfMessages = useSelector((state: RootState) => state.emailList.messages)
    const tabNumber = useSelector((state: RootState) => state.emailList.primaryTabNumber);

    useEffect(() => {
        dispatch(fetchAllMessages());
    }, [dispatch])

    const statusMap: { [key: number]: string } = {
        1: "NEW",
        7: "SENT",
        10: "DRAFT",
        15: "FOLLOW",
        20: "TRASH"
    };

    const handleListOfTab = (typeOfTab: number) => {
        const status = statusMap[typeOfTab];
    
        const filteredListOfMessages = listOfMessages.filter(message => message.status === status);
        console.log(filteredListOfMessages)
        return filteredListOfMessages;
    };

    useEffect(() => {
        handleListOfTab(tabNumber)
    }, [tabNumber])

    return (
    <Paper sx={{ height: '100vh', width: '100%', ml: '1%' }}>
      <DataGrid
        rows={rows}
        columns={columns}
        initialState={{ pagination: { paginationModel } }}
        pageSizeOptions={[5, 10]}
        checkboxSelection
        sx={{ border: 0 }}
      />
    </Paper>
    );
}

export default MainListOfEmails;