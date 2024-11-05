import { configureStore } from "@reduxjs/toolkit";
import emailCreatorSlice from "./slices/emailSlices/emailCreator-slice";
import editTextReducer from "./slices/emailSlices/editText-slice";
import signInSlice from "./slices/authSlices/signIn-slice";
import findUserOrClientEmailSlice from "./slices/emailSlices/findUserOrClientEmail-slice";
import sendEmailSliice from "./slices/emailSlices/sendEmail-slice";
import emailListSlice from "./slices/emailSlices/emailList-slice";

const store = configureStore({
  reducer: {
    emailCreator: emailCreatorSlice.reducer,
    editText: editTextReducer.reducer,
    signIn: signInSlice.reducer,
    findUserOrClientEmail: findUserOrClientEmailSlice.reducer,
    sendEmail: sendEmailSliice.reducer,
    emailList: emailListSlice.reducer
  },
  devTools: process.env.NODE_ENV !== 'production',
});

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch


export default store;