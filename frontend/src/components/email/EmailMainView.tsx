import Navigation from "./navigation/Navigation";
import EmailCreator from "./emailCreator/EmailCreator";
import MainListOfEmails from "./emailList/MainListOfEmails";
import { Box } from "@mui/material";

const EmailMainView = () => {
    return(
        <Box component="section" sx={{width: '100%', height: '100%', display: "flex", overflow: "hidden" }}>
            <Navigation />
            <EmailCreator />
            <MainListOfEmails />
        </Box>
    )
}

export default EmailMainView;