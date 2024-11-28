import axios from "axios";
import { AppDispatch } from "..";
import { emailListAction } from "../slices/emailSlices/emailList-slice";
import { handleError } from "./helperFunctions/handleError";

type DeleteMessageParams = {
    folderId: number;
    messageId?: number;
};

export const deleteMessageFromFolder = ({ folderId, messageId }: DeleteMessageParams) => {
    return async (dispatch: AppDispatch) => {
        try {
            const url = messageId
                ? `http://localdev:8082/api/message-folders/${folderId}/messages/${messageId}`
                : `http://localhost:8082/api/message-folders/${folderId}/messages`;

            const response = await axios.delete(url);

            if (response.status === 200) {
                const successMessage = messageId
                    ? "Message have been deleted"
                    : "All messages have been deleted from the folder.";
                dispatch(
                    emailListAction.setFolderMessageRequestStatus({
                        status: "success",
                        message: successMessage,
                    })
                );
            } else {
                console.warn(`Unexpected response status: ${response.status}`);
            }
        } catch (error) {
            dispatch(
                emailListAction.setFolderMessageRequestStatus({
                    status: "error",
                    message: handleError(error),
                })
            );
        }
    };
};
