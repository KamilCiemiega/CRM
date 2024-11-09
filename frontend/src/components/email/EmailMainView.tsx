import Navigation from "./navigation/Navigation";
import EmailCreator from "./emailCreator/EmailCreator";
import MainListOfEmails from "./emailList/MainListOfEmails";
import EmailPreviewTextFields from "./emailList/emailPreview/EmailPreviewTextFields";
import { CircularProgress, Backdrop } from "@mui/material";
import { useSelector } from "react-redux";
import { RootState } from "../store";
import StyledBox from "../../style/EmailMainViewStyle";
import { useEffect, useState } from "react";
import useParticipantsData from "../../hooks/useParticipantData";

const EmailMainView = () => {
    const [open, setOpen] = useState(false);
    const showMessagePreview = useSelector((state: RootState) => state.emailPreview.showMessagePreview);
    const loadingParticipantsState = useSelector((state: RootState) => state.emailPreview.dataToDisplay.participant);
    const messageRoles = useSelector((state: RootState) => state.emailPreview.messageRoles);
    const { loadingData } = useParticipantsData();

    return(
        <StyledBox>
            <Backdrop sx={(theme) => ({ color: '#fff', zIndex: theme.zIndex.drawer + 1 })}
                open={loadingData}>
            <CircularProgress size="6rem" />
            </Backdrop> 
                <Navigation />
                <EmailCreator />
                {showMessagePreview ? <EmailPreviewTextFields /> : <MainListOfEmails />}
        </StyledBox>
    )
}

export default EmailMainView;