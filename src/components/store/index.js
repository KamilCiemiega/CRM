import { configureStore } from "@reduxjs/toolkit";
import emailCreatorSlice from "./emailCreator-slice";
import editTextSlice from "./editText-slice";
import signInSlice from "./signIn-slice";

const store = configureStore({
  reducer: {
    emailCreator: emailCreatorSlice.reducer,
    editText: editTextSlice.reducer,
    signIn: signInSlice.reducer,
  },
});

export default store;
