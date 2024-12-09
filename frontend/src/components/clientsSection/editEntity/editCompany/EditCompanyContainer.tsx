import { Box, Paper } from "@mui/material";
import CompanyTextFields from "../../newEntity/CompanyTextFields";
import ListOfClients from "./listOfClients/ListOfClients";
import "../../../../style/EditCompanyContainer.css";

const EditCompanyContainer = () => {

    return (
        <Box className="editContainer">
            <Box className="clientsContentContainer">
                <Box className="companyTextFields">
                <CompanyTextFields />
                </Box>
                <ListOfClients />
            </Box>
               
        </Box>
    );

}

export default EditCompanyContainer;