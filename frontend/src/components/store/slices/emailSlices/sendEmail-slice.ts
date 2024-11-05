import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { MessageRoles, SendMessageStatus } from "../../../../interfaces/interfaces";
import { RawDraftContentState } from "draft-js";

interface State {
    subtitleValue: string;
    messageRoles: MessageRoles[];
    editorContent: RawDraftContentState;
    sendMessageStatus: SendMessageStatus
}

const initialState: State = {
    subtitleValue: "",
    messageRoles: [],
    editorContent: {blocks: [], entityMap: {}},
    sendMessageStatus: {status: "", message: "", openAlert: false}
};

const sendEmailSlice = createSlice({
    name: "sendEmail",
    initialState,
    reducers: {
        setSubtitleValue(state, action: PayloadAction<string>) {
            state.subtitleValue = action.payload;
        },
        addMessageRole(state, action: PayloadAction<MessageRoles>) {
            state.messageRoles.push(action.payload);
        },
        setEditorContent(state, action: PayloadAction<RawDraftContentState>) {
            state.editorContent = action.payload;
        },
        setSendMessageStatus(state, action: PayloadAction<SendMessageStatus>) {
            state.sendMessageStatus = action.payload;
        }

    }
});

export const sendEmailAction = sendEmailSlice.actions;
export default sendEmailSlice;
