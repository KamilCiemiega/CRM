import { createSlice } from "@reduxjs/toolkit";
import { jwtDecode } from "jwt-decode";

const initialState = {
    googleCredential: {},
    loggedInUser: {
      name: "",
      surname: ""
    }
};

const signInSlice = createSlice({
    name: "signIn",
    initialState,
    reducers: {
            setGoogleCredentials(state, action){
                state.googleCredential = action.payload
            },
            setLoggedInUserData(state,action){
              state.loggedInUser.name = action.payload.firstName
              state.loggedInUser.surname = action.payload.lastName
            }
        }
});

export const updateGoogleCredentials = (credentialResponse) => async (
    dispatch
  ) => {
    try {
      const credentialResponseDecoded = jwtDecode(
        credentialResponse.credential
      );
      dispatch(signInSlice.actions.setGoogleCredentials(credentialResponseDecoded));
    } catch (error) {
      console.log("Error:", error);
    }
  };

export const signInAction = signInSlice.actions;

export default signInSlice;