import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    loggedInUser: {
      name: "",
      surname: ""
    }
};

const signInSlice = createSlice({
    name: "signIn",
    initialState,
    reducers: {
            setLoggedInUserData(state,action){
              state.loggedInUser.name = action.payload.firstName
              state.loggedInUser.surname = action.payload.lastName
            }
        }
});

export const signInAction = signInSlice.actions;

export default signInSlice;