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
import { filterData } from "./filterData";
import { UserAndClient } from "../../../../interfaces/UserAndClient";


const FindUserOrClientEmail = () => {
  const [filteredUsers, setFilteredUsers] = useState<UserAndClient[]>([]);
  const [filteredClients, setFilteredClients] = useState<UserAndClient[]>([]);
  const dispatch = useDispatch<AppDispatch>();
  const users = useSelector(
    (state: RootState) => state.findUserOrClientEmail.users
  );
  const clients = useSelector(
    (state: RootState) => state.findUserOrClientEmail.clients
  );
  const toInputValueState = useSelector(
    (state: RootState) => state.findUserOrClientEmail.toInputValue
  );
  const ccInputValueState = useSelector(
    (state: RootState) => state.findUserOrClientEmail.ccInputValue
  );
  const openCcSearchBox = useSelector(
    (state: RootState) => state.findUserOrClientEmail.openCcSearchBox
  );
  const openToSearchBox = useSelector(
    (state: RootState) => state.findUserOrClientEmail.openToSearchBox
  );


  const handleFilterData = (inputValue: string) => {
    const { filteredUsers, filteredClients } = filterData(
      inputValue,
      users,
      clients,
      openToSearchBox,
      openCcSearchBox,
      dispatch
    );

    setFilteredUsers(filteredUsers);
    setFilteredClients(filteredClients);
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
  
  // useEffect(() => {
  //   if (users.length > 0 && clients.length > 0) {
  //     if (ccInputValueState) {
  //       handleFilterData(ccInputValueState);
  //     }
  //   }
  // }, [ccInputValueState, users, clients]);

  const onClickHandler = (email: string) => {
    if(openToSearchBox){
      dispatch(findUserOrClientEmailAction.setToAllInputValue(email))
      dispatch(findUserOrClientEmailAction.setToInputValue(email));
      dispatch(findUserOrClientEmailAction.setOpenToSearchBox(false));
    }else {
      dispatch(findUserOrClientEmailAction.setCcInputValue(email));
      dispatch(findUserOrClientEmailAction.setOpenCcSearchBox(false));
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
              onClick={() => onClickHandler(`${user.email},`)}
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
              onClick={() => onClickHandler(client.email)}
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
