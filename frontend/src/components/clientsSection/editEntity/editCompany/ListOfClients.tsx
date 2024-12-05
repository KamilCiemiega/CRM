import { Box } from "@mui/material";
import { useSelector } from "react-redux";
import { RootState } from "../../../store";

const ListOfClients = () => {
    const clickedEntityData = useSelector((state: RootState) => state.clientView.clickedEntity);

    return(
        <Box></Box>
    );
}

export default ListOfClients;