import { configureStore } from "@reduxjs/toolkit";
import emailCreatorSlice from "./emailCreator-slice";
import editTextReducer from "./editText-slice";
import signInSlice from "./signIn-slice";
import findUserOrClientEmailSlice from "./findUserOrClientEmail-slice";

const store = configureStore({
  reducer: {
    emailCreator: emailCreatorSlice.reducer,
    editText: editTextReducer.reducer,
    signIn: signInSlice.reducer,
    findUserOrClientEmail: findUserOrClientEmailSlice.reducer
  },
});

export default store;