import { Box} from "@mui/material";
import TopPanel from "./panelElements/TopPanel";
import DraggableArea from "./panelElements/DrraggableArea";

const CrmPanelMainView = () => {
   return (
    <Box
    sx={{
        width: "100%",
        height: "100vh",
        overflow: "hidden"
    }}
>
    <TopPanel />
    <DraggableArea />
</Box>
   );
}

export default CrmPanelMainView;