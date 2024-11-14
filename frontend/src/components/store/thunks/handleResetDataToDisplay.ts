import { AppDispatch } from "..";
import { emailPreviewAction } from "../slices/emailSlices/emailPreview-slice";
import { emailListAction } from "../slices/emailSlices/emailList-slice";

export const handleResetDataToDisplay = () => (dispatch: AppDispatch) => {
    dispatch(emailPreviewAction.setDataToDisplay([]));
    dispatch(emailPreviewAction.setDataToDisplay({body: "", subtitle: "", attachmentsNumber: 0}));
    dispatch(emailListAction.setClickedChecboxes([]));
}

