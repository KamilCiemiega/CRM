import axios from "axios";
import { AppDispatch } from "../../../store";
import { sendEmailAction } from "../../../store/slices/emailSlices/sendEmail-slice";

export const SendNewMessageRequest = async (
  payload: {},
  navigate: (path: string) => void,
  dispatch: AppDispatch
) => {
  try {
    const response = await axios.post("http://localdev:8082/api/messages", payload);
    if (response.status === 201) {
      navigate('/emailView');
      dispatch(sendEmailAction.setSendMessageStatus({
        status: "success",
        message: "Message send success",
        openAlert: true
      }));
    }
  } catch (error: unknown) {
    let errorMessage = "An unknown error occurred";
    if (axios.isAxiosError(error)) {
      errorMessage = error.message;
    } else if (error instanceof Error) {
      errorMessage = error.message;
    }
    dispatch(sendEmailAction.setSendMessageStatus({
      status: "error",
      message: errorMessage,
      openAlert: true
    }));
  }
};
