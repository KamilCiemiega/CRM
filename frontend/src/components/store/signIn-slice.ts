import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface UserData {
  firstName: string;
  lastName: string;
}

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
            setLoggedInUserData(state,action: PayloadAction<UserData>){
              state.loggedInUser.name = action.payload.firstName
              state.loggedInUser.surname = action.payload.lastName
            }
        }
});

export const signInAction = signInSlice.actions;

export default signInSlice;