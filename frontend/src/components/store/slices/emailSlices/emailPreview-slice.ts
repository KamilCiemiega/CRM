import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { DataToDisplay } from "../../../../interfaces/interfaces";

interface State {
   dataToDisplay: DataToDisplay
}

const initialState: State = {
    dataToDisplay:  {
        body: "",
        attachmentsNumber: 0,
        participant: {
          status: "",
          email: ""
        }
      }
};

const emailPreviewSlice = createSlice({
    name: "emailPreview",
    initialState,
    reducers: {
       
    }
});

export const emailPreviewAction = emailPreviewSlice.actions;
export default emailPreviewSlice;
