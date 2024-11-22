import axios from "axios";
import { AppDispatch } from "..";
import { emailListAction } from "../slices/emailSlices/emailList-slice";

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
            const errorMessage = axios.isAxiosError(error)
                ? `Axios error: ${error.message}`
                : error instanceof Error
                ? `General error: ${error.message}`
                : "Unknown error occurred";

            dispatch(
                emailListAction.setFolderMessageRequestStatus({
                    status: "error",
                    message: errorMessage,
                })
            );
        }
    };
};
