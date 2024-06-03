import { useSelector, useDispatch } from "react-redux";
import { Box, Typography, Badge } from "@mui/material";
import PersonIcon from "@mui/icons-material/Person";
import axios from "axios";
import React, { useEffect, useState } from "react";
import StyledTypography from "../../../style/FindUserOrClientEmailStyle";
import { findUserOrClientEmailAction } from "../../store/findUserOrClientEmail-slice";

const FindUserOrClientEmail = () => {
  const [users, setUsers] = useState([]);
  const [clients, setClients] = useState([]);
  const [filteredUsers, setFilteredUsers] = useState([]);
  const [filteredClients, setFilteredClients] = useState([]);
  const dispatch = useDispatch();
  const toInputValueState = useSelector(
    state => state.findUserOrClientEmail.toInputValue
  );
  const ccInputValueState = useSelector(
    state => state.findUserOrClientEmail.ccInputValue
  );
  const openCcSearchBox = useSelector(
    state => state.findUserOrClientEmail.openCcSearchBox
  );
  const openToSearchBox = useSelector(
    state => state.findUserOrClientEmail.openToSearchBox
  );


  const handleEmail = async () => {
    try {
      const usersResponse = await axios.get(
        "http://localdev:8082/api/auth/get-users"
      );
      const clientsResponse = await axios.get(
        "http://localdev:8082/api/client/get-clients"
      );

      setUsers(usersResponse.data);
      setClients(clientsResponse.data);
    } catch (error) {
      console.log(error.message);
    }
  };

  const filterData = (inputValue) => {
    if (inputValue.length > 0) {
      const lowerCaseInput = inputValue.toLowerCase();

      const filteredUsers = users.filter((user) =>
        user.email.toLowerCase().includes(lowerCaseInput)
      );

      const filteredClients = clients.filter((client) =>
        client.email.toLowerCase().includes(lowerCaseInput)
      );

      setFilteredUsers(filteredUsers);
      setFilteredClients(filteredClients);
      
      if (filteredUsers.length === 0 && filteredClients.length === 0) {
        if (openToSearchBox) {
          dispatch(findUserOrClientEmailAction.setOpenToSearchBox(false));
        }
        if (openCcSearchBox) {
          dispatch(findUserOrClientEmailAction.setOpenCcSearchBox(false));
        }
      }
      
    } else {
      setFilteredUsers([]);
      setFilteredClients([]);
    }
  };

  useEffect(() => {
    handleEmail();
  }, []);

  useEffect(() => {
    if (users.length > 0 && clients.length > 0) {
      if (toInputValueState) {
        filterData(toInputValueState);
      }
    }
  }, [toInputValueState, users, clients]);
  
  useEffect(() => {
    if (users.length > 0 && clients.length > 0) {
      if (ccInputValueState) {
        filterData(ccInputValueState);
      }
    }
  }, [ccInputValueState, users, clients]);

  const onClickHandler = (email) => {
    if(openToSearchBox){
      dispatch(findUserOrClientEmailAction.setToInputValue(email));
      dispatch(findUserOrClientEmailAction.setOpenToSearchBox(false));
    }else {
      dispatch(findUserOrClientEmailAction.setCcInputValue(email));
      dispatch(findUserOrClientEmailAction.setOpenCcSearchBox(false));
    }
  };

  return (
    <Box
      component="section"
      sx={{
        height: "auto",
        background: "#1976d2",
        width: "93%",
        position: "absolute",
        top: openCcSearchBox === true ? "214px" : "145px",
        left: "52px",
        cursor: "pointer",
        boxShadow: "4px 16px 24px -5px rgba(66, 68, 90, 1)",
        display: "flex",
        alignItems: "center",
        justifyContent: "flex-start",
        padding: "8px",
        zIndex: 1,
        padding: "1px",
      }}
    >
      <Box sx={{ width: "100%", padding: "2%" }}>
        <Box sx={{ width: "100%" }}>
          <Box sx={{ display: "flex", alignItems: "center" }}>
            <Badge badgeContent={filteredUsers.length} color="primary">
              <PersonIcon sx={{ color: "white" }} />
            </Badge>
            <Typography variant="h6" sx={{ marginLeft: "2%", color: "white" }}>
              Users:
            </Typography>
          </Box>
          {filteredUsers.map((user) => (
            <StyledTypography
              key={user.id}
              onClick={() => onClickHandler(user.email)}
            >
              {user.email}
            </StyledTypography>
          ))}
        </Box>
        <Box sx={{ width: "100%" }}>
          <Box sx={{ display: "flex", alignItems: "center", marginTop: "2%" }}>
            <Badge badgeContent={filteredClients.length} color="primary">
              <PersonIcon sx={{ color: "white" }} />
            </Badge>
            <Typography variant="h6" sx={{ marginLeft: "2%", color: "white" }}>
              Clients:
            </Typography>
          </Box>
          {filteredClients.map((client) => (
            <StyledTypography
              onClick={() => onClickHandler(client.email)}
              key={client.id}
            >
              {client.email}
            </StyledTypography>
          ))}
        </Box>
      </Box>
    </Box>
  );
};

export default FindUserOrClientEmail;
