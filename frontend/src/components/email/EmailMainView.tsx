import Navigation from "./navigation/Navigation";
import EmailCreator from "./emailCreator/EmailCreator";
import MainListOfEmails from "./emailList/mainEmailFunctions/MainListOfEmails";
import { CircularProgress, Backdrop } from "@mui/material";
import { useSelector } from "react-redux";
import { RootState } from "../store";
import StyledBox from "../../style/EmailMainViewStyle";
import useParticipantsData from "../../hooks/useParticipantData";
import EmailPreviewContainer from "./emailList/emailPreview/EmailPreviewContainer";

const EmailMainView = () => {
    const showMessagePreview = useSelector((state: RootState) => state.emailPreview.showMessagePreview);
    const { loadingData } = useParticipantsData();

    return(
        <StyledBox>
            <Backdrop sx={(theme) => ({ color: '#fff', zIndex: theme.zIndex.drawer + 1 })}
                open={loadingData}>
            <CircularProgress size="6rem" />
            </Backdrop> 
                <Navigation />
                <EmailCreator />
                {showMessagePreview ? <EmailPreviewContainer />: <MainListOfEmails />}
        </StyledBox>
    )
}

export default EmailMainView;