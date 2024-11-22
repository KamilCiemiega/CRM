import { ExpandLess, ExpandMore, Person, Dashboard, Logout, ManageAccounts } from "@mui/icons-material";
import { Collapse, List, ListItemButton, ListItemIcon, ListItemText } from "@mui/material";
import { useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { HandleLogOutUser } from "./helperFunction.ts/HandleLogOutUser";
import { useNavigate } from "react-router-dom";

const UserPanelList = () => {
    const navigate = useNavigate();
    const [open, setOpen] = useState(false);
    const loggedInUserCredentials = useSelector((state: RootState) => state.signIn.loggedInUser);

    const handleClick = () => {
        setOpen(!open);
    }

    const handleNavigate = (path: string) => {
      navigate(`${path}`);
    }

    return (
        <>
        <List
        sx={{ width: '100%'}}
        component="nav"
        aria-labelledby="nested-list-subheader"
      >
        <ListItemButton onClick={handleClick}>
          <ListItemIcon>
            <Person />
          </ListItemIcon>
          {/* {loggedInUserCredentials.name} {loggedInUserCredentials.surname} */}
          <ListItemText primary="Kamil Ciemiega" />  
          {open ? <ExpandLess /> : <ExpandMore />}
        </ListItemButton>
        <Collapse in={open} timeout="auto" unmountOnExit>
          <List component="div" disablePadding>
          <ListItemButton sx={{ pl: 2, cursor: 'pointer'}} onClick={() => handleNavigate('/cmrPanel')}>
            <ListItemIcon>
              <Dashboard  sx={{fontSize: '18px'}}/>
            </ListItemIcon>
            <ListItemText sx={{fontSize: '10px'}} primary="Dashboard" />
          </ListItemButton>
          <ListItemButton sx={{ pl: 2, cursor: 'pointer'}}>
            <ListItemIcon>
              <ManageAccounts />
            </ListItemIcon>
            <ListItemText sx={{fontSize: '10px'}} primary="Profile" />
          </ListItemButton>
          <ListItemButton sx={{ pl: 2, cursor: 'pointer'}} onClick={() => HandleLogOutUser(navigate)}>
            <ListItemIcon>
              <Logout sx={{fontSize: '22px'}}/>
            </ListItemIcon>
            <ListItemText sx={{fontSize: '10px'}} primary="Log Out" />
          </ListItemButton>
          </List>
        </Collapse>
        </List>
        </>
    )
}

export default UserPanelList;