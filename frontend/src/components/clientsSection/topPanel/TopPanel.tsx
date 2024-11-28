import { Business, AccountBox, Add, Visibility } from "@mui/icons-material";
import { Box, SpeedDial, SpeedDialAction } from "@mui/material";
import { clientViewAction } from "../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import { AppDispatch } from "../../store";
import { useDispatch } from "react-redux";

const TopPanel = () => {
    const dispatch = useDispatch<AppDispatch>();

    const actions = [
        { icon: <Business />, name: 'Companies view', path: 'companies'},
        { icon: <AccountBox />, name: 'Clients view', path: 'clients'},
        { icon: <Add />, name: 'Add new', path: 'add'},
    ];

    return(
        <Box sx={{width: '100%',height: '100px',backgroundColor: '#363636'}}>
              <SpeedDial
                ariaLabel="SpeedDial basic example"
                sx={{ position: 'absolute', top: 20, left: 16 }}
                icon={<Visibility />}
                direction="right"
            >
                {actions.map((action) => (
                    <SpeedDialAction
                        key={action.name}
                        icon={action.icon}
                        tooltipTitle={action.name}
                        onClick={() => dispatch(clientViewAction.setViewType(action.path))}
                    />
                ))}
            </SpeedDial>
        </Box>
    );
}

export default TopPanel;