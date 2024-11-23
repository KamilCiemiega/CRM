import { Box, SpeedDial, SpeedDialAction, Typography } from "@mui/material";
import { Email, Person } from "@mui/icons-material";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";

const TopPanel = () => {
    const navigate = useNavigate();
    
    const actions = [
        { icon: <Email />, name: 'Email view', path: '/emailView'},
        { icon: <Person />, name: 'Account settings', path: '/emailView'},
    ];

    return (
        <Box
            sx={{width: '100%',height: '100px',backgroundColor: '#121621',}}>
            <SpeedDial
                ariaLabel="SpeedDial basic example"
                sx={{ position: 'absolute', top: 20, right: 16 }}
                icon={<Person />}
                direction="left"
            >
                {actions.map((action) => (
                    <SpeedDialAction
                        key={action.name}
                        icon={action.icon}
                        tooltipTitle={action.name}
                        onClick={() => navigate(action.path)}
                    />
                ))}
            </SpeedDial>
        </Box>
    );
};

export default TopPanel;
