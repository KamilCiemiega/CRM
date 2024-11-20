import { useSelector, useDispatch } from "react-redux";
import { useEffect, useState } from "react";
import { Paper } from '@mui/material';
import { fetchAllMessages } from "../../../store/thunks/fetchAllMessages";
import { AppDispatch, RootState } from "../../../store";
import TableDataComponent from "./TableDataComponent";
import { emailListAction } from "../../../store/slices/emailSlices/emailList-slice";
import useDeleteEmail from "../hooks/useDeleteEmail";


const MainListOfEmails = () => {
    const dispatch = useDispatch<AppDispatch>();
    const listOfMessages = useSelector((state: RootState) => state.emailList.messages)
    const tabNumber = useSelector((state: RootState) => state.emailList.primaryTabNumber);
    const primaryTabNumber = useSelector((state: RootState) => state.emailList.primaryTabNumber);
    const updatedMessageFolderStatus = useSelector((state: RootState) => state.emailList.changeFolderMessageRequestStatus);
    useDeleteEmail();

    useEffect(() => {
        dispatch(fetchAllMessages());
    }, [primaryTabNumber, updatedMessageFolderStatus])

    const statusMap: { [key: number]: string } = {    
        1: "INBOX",
        8: "SENT",
        12: "DRAFT",
        17: "FAVORITE",
        22: "TRASH"
    };

    const handleListOfTab = (typeOfTab: number) => {
        const status = statusMap[typeOfTab];
    
        if(listOfMessages.length > 0) {
            const filteredListOfMessages = listOfMessages.filter(m => 
                m.messageFolders.some(mF => mF.name === status)
            );
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