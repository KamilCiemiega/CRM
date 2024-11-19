import { useSelector, useDispatch } from "react-redux";
import { useEffect, useState } from "react";
import { Alert, Paper } from '@mui/material';
import { fetchAllMessages } from "../../../store/thunks/fetchAllMessages";
import { AppDispatch, RootState } from "../../../store";
import TableDataComponent from "./TableDataComponent";
import { emailListAction } from "../../../store/slices/emailSlices/emailList-slice";


const MainListOfEmails = () => {
    const dispatch = useDispatch<AppDispatch>();
    const listOfMessages = useSelector((state: RootState) => state.emailList.messages)
    const tabNumber = useSelector((state: RootState) => state.emailList.primaryTabNumber);
    const sendMessageStatus = useSelector((state: RootState) => state.sendEmail.sendMessageStatus);

    useEffect(() => {
        dispatch(fetchAllMessages());
    }, [dispatch, sendMessageStatus])

    const statusMap: { [key: number]: string } = {
        1: "NEW",
        8: "SENT",
        12: "DRAFT",
        17: "FOLLOW",
        22: "TRASH"
    };

    const handleListOfTab = (typeOfTab: number) => {
        const status = statusMap[typeOfTab];
    
        if(listOfMessages.length > 0) {
            const filteredListOfMessages = listOfMessages.filter(message => message.status === status);
            dispatch(emailListAction.setFiltredMessages(filteredListOfMessages));
        }
    };

    useEffect(() => {
        handleListOfTab(tabNumber)
    }, [tabNumber, listOfMessages])

    return (
    <Paper sx={{ height: '100vh', width: '100%', ml: '1%' }}>
        <TableDataComponent />
    </Paper>
    );
}

export default MainListOfEmails;