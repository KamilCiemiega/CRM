import { Business, AccountBox, Add, Visibility } from "@mui/icons-material";
import { Box, Button, SpeedDial, SpeedDialAction } from "@mui/material";
import { clientViewAction } from "../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import { AppDispatch } from "../../store";
import { useDispatch } from "react-redux";
import SearchBox from "./SearchBox";


const TopPanel = () => {
    const dispatch = useDispatch<AppDispatch>();

    const actions: { icon: JSX.Element; name: string; path: 'clients' | 'companies' }[] = [
        { icon: <Business />, name: 'Companies view', path: 'companies' },
        { icon: <AccountBox />, name: 'Clients view', path: 'clients' }
    ];

    return(
        <Box sx={{
            width: '100%',
            height: '100px',
            backgroundColor: '#363636',
            display: 'flex',
            justifyContent: 'center', 
            alignItems: 'center'}}>
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
            <SearchBox />
            <Button 
            variant="contained" 
            sx={{position: 'absolute', 
            right: '16px'}}
            onClick={() => dispatch(clientViewAction.setOpenNewEntityDialog(true))}
            >
                Add new
            </Button>
        </Box>
    );
}

export default TopPanel;