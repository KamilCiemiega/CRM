import { useState, cloneElement, ReactElement } from "react";
import {
  ListItemIcon,
  ListItemText,
  ListItemButton,
  List,
  Typography,
  Collapse,
  Box
} from "@mui/material";
import {ExpandMore, ExpandLess, Logout } from "@mui/icons-material";
import navigationData from "./NavigationSchema";
import { useDispatch, useSelector } from "react-redux";
import { emailCreatorAction } from "../../store/slices/emailSlices/emailCreator-slice";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { RootState } from "../../store";
import { NavigationItemProps } from "../../../interfaces/interfaces";
import { emailListAction } from "../../store/slices/emailSlices/emailList-slice";

const Navigation = () => {
  const [openItems, setOpenItems] = useState<number[]>([]);
  const [activeItem, setActiveItem] = useState(1);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const loggedInUserCredentials = useSelector((state: RootState) => state.signIn.loggedInUser);

  const handleClick = (index: number) => {
    dispatch(emailListAction.setPrimaryTabNumber(index));

    if (openItems.includes(index)) {
      setOpenItems(openItems.filter((item) => item !== index));
    } else {
      setOpenItems([...openItems, index]);
    }
    setActiveItem(index);

    if(index === 2){
      dispatch(
        emailCreatorAction.setOpenDialog(true));
    }
  };

  const handleLogoutUser = async () => {
    try{
      const response = await axios.get("http://localdev:8082/api/users/logout", {
        withCredentials: true
      });
      if(response.status === 200){
        navigate('/');
    }
    }catch (error: unknown){
      if (axios.isAxiosError(error)) {
          console.log(error.message);
        }else if (error instanceof Error) {
          console.log(error.message);
        }
    }
  }

  const NavigationItem: React.FC<NavigationItemProps> = ({
    icon,
    primary,
    index,
    openItems,
    activeItem,
    handleClick,
    collapseItems,
  }) => {
    return (
      <Box sx={{width: '300px'}}>
        <ListItemButton
          onClick={() => handleClick(index)}
          sx={{ bgcolor: activeItem === index ? "#625bff" : "" }}
        >
          <ListItemIcon sx={{ color: "white" }}>{icon}</ListItemIcon>
          <ListItemText primary={primary} />
          {openItems.includes(index) ? <ExpandLess /> : <ExpandMore />}
        </ListItemButton>
        <Collapse in={openItems.includes(index)} timeout="auto" unmountOnExit>
          <List component="div" disablePadding>
            {collapseItems.map((collapseItem, idx) => (
              <ListItemButton
                key={idx}
                onClick={() => handleClick(collapseItem.index)}
                sx={{
                  bgcolor: activeItem === collapseItem.index ? "#625bff" : "",
                }}
              >
                <ListItemIcon sx={{ color: "white", fontSize: "" }}>
                  {cloneElement(collapseItem.icon, {
                    style: { fontSize: "medium" },
                  })}
                </ListItemIcon>
                <ListItemText
                  primary={
                    <Typography variant="body1" style={{ fontSize: "14px" }}>
                      {collapseItem.primary}
                    </Typography>
                  }
                />
              </ListItemButton>
            ))}
          </List>
        </Collapse>
      </Box>
    );
  };

  return (
    <List
      sx={{
        "& .MuiListItemButton-root:hover": {
          bgcolor: "#6f6f71",
        },
        height: "100vh",
        background: "#121621",
        color: "white",
        maxWidth: "320px",
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-between",
      }}
      component="nav"
      aria-labelledby="nested-list-subheader"
    >
      <Box>
        {navigationData.map((item, index) => (
          <NavigationItem
            key={index}
            icon={item.icon}
            primary={item.primary}
            index={item.index}
            openItems={openItems}
            activeItem={activeItem}
            handleClick={handleClick}
            collapseItems={item.collapseItems}
          />
        ))}
      </Box>
      <Box sx={{ display: "flex", alignItems: "center", ml: "2%" }}>
        <Logout sx={{cursor: "pointer"}} onClick={() => handleLogoutUser()}/>
        <Typography variant="subtitle1" sx={{ marginLeft: "8px" }}>
          {loggedInUserCredentials.name} {loggedInUserCredentials.surname}
        </Typography>
      </Box>
    </List>
  );
};

export default Navigation;
