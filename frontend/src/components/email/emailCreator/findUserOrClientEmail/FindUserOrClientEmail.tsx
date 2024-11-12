import { useSelector, useDispatch } from "react-redux";
import { Box, Typography, Badge } from "@mui/material";
import PersonIcon from "@mui/icons-material/Person";
import { useEffect, useState } from "react";
import StyledTypography from "../../../../style/FindUserOrClientEmailStyle";
import { findUserOrClientEmailAction } from "../../../store/slices/emailSlices/findUserOrClientEmail-slice";
import { RootState } from "../../../store";
import { fetchUserAndClientData } from "../../../store/thunks/fetchUserAndClientData";
import { AppDispatch } from "../../../store";
import RenderBox from "../../../../style/RenderBox";
import { filterData } from "./helperFunctions/filterData";
import { UserAndClient } from "../../../../interfaces/interfaces";
import axios from "axios";
import { MessageRoles } from "../../../../interfaces/interfaces";
import { sendEmailAction } from "../../../store/slices/emailSlices/sendEmail-slice";


const FindUserOrClientEmail = () => {
  const [filteredUsers, setFilteredUsers] = useState<UserAndClient[]>([]);
  const [filteredClients, setFilteredClients] = useState<UserAndClient[]>([]);
  const dispatch = useDispatch<AppDispatch>();
  const users = useSelector((state: RootState) => state.findUserOrClientEmail.users);
  const clients = useSelector((state: RootState) => state.findUserOrClientEmail.clients);
  const toInputValueState = useSelector((state: RootState) => state.findUserOrClientEmail.toInputValue);
  const ccInputValueState = useSelector((state: RootState) => state.findUserOrClientEmail.ccInputValue);
  const openCcSearchBox = useSelector((state: RootState) => state.findUserOrClientEmail.openCcSearchBox);
  const openToSearchBox = useSelector((state: RootState) => state.findUserOrClientEmail.openToSearchBox);

  const handleFilterData = (inputValue: string) => {
    const { filteredUsers, filteredClients,  matchingValue} = filterData(
      inputValue,
      users,
      clients,
      openToSearchBox,
      openCcSearchBox,
      dispatch
    );

    setFilteredUsers(filteredUsers);
    setFilteredClients(filteredClients);
    if(matchingValue && matchingValue.length > 0){
      dispatch(findUserOrClientEmailAction.setValueToTrim(matchingValue));
    }
  };

  useEffect(() => {
     dispatch(fetchUserAndClientData());
  }, [dispatch]);

  useEffect(() => {
    if (users.length > 0 && clients.length > 0) {
      if (toInputValueState) {
        handleFilterData(toInputValueState);
      }
    }
  }, [toInputValueState, users, clients]);
  
  useEffect(() => {
    if (users.length > 0 && clients.length > 0) {
      if (ccInputValueState) {
        handleFilterData(ccInputValueState);
      }
    }

  }, [ccInputValueState, users, clients]);

  const getParticipiantFromBackend = async (type: string, id: number) => {
    let fieldType = "";
    if(openToSearchBox){
      fieldType = "TO"
    }else {
      fieldType = "CC"
    }
      try{
        const response = await axios.get(`http://localdev:8082/api/message-participant/by-type-and-id?type=${type.toUpperCase()}&${type}Id=${id}`)
        const messageRoles: MessageRoles = {
          "status": fieldType,
          "participantId": response.data.id
        }
        dispatch(sendEmailAction.addMessageRole(messageRoles));

      }catch(error: unknown) {
        if (axios.isAxiosError(error)) {
          console.log(error.message);
        } else if (error instanceof Error) {
          console.log(error.message);
        }
      }
  }

  const onClickHandler = async (email: string, id: number, type: 'user' | 'client') => {
    if (openToSearchBox) {
      dispatch(findUserOrClientEmailAction.setToInputValue({ value: email, valuType: "filtredValue" }));
      dispatch(findUserOrClientEmailAction.setOpenToSearchBox(false));
    } else if (openCcSearchBox) {
      dispatch(findUserOrClientEmailAction.setCcInputValue({ value: email, valuType: "filtredValue" }));
      dispatch(findUserOrClientEmailAction.setOpenCcSearchBox(false));
    }

    try {
       await getParticipiantFromBackend(type, id);

    }catch (error){
      console.log("Error while tring to get participant ", error);
    }
  };
  

  return RenderBox({
    openCcSearchBox,
    children: (
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
          {filteredUsers.map((user, i) => (
            <StyledTypography
              key={i}
              onClick={() => onClickHandler(user.email, user.id, 'user')}
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
          {filteredClients.map((client, i) => (
            <StyledTypography
              onClick={() => onClickHandler(client.email, client.id, 'client')}
              key={i}
            >
              {client.email}
            </StyledTypography>
          ))}
        </Box>
      </Box>
    ),
  });
};

export default FindUserOrClientEmail;
