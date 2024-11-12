import { useSelector, useDispatch } from "react-redux";
import { useEffect } from "react";
import { RootState } from "../components/store";
import { emailCreatorAction } from "../components/store/slices/emailSlices/emailCreator-slice";
import { findUserOrClientEmailAction } from "../components/store/slices/emailSlices/findUserOrClientEmail-slice";
import { sendEmailAction } from "../components/store/slices/emailSlices/sendEmail-slice";
import { emailPreviewAction } from "../components/store/slices/emailSlices/emailPreview-slice";

const  useSubFoldersEmailActions = () => {
const dataToDisplay = useSelector((state: RootState) => state.emailPreview.dataToDisplay);
const clickedCheckBoxes = useSelector((state: RootState) => state.emailList.clickedCheckboxes);
const secondaryTabNumber = useSelector((state: RootState) => state.emailList.secondaryTabNumber);
const dispatch = useDispatch();

const chooseSendMessageType: { [key: number]: string } = {
    3: "REPLAY",
    4: "REPLAYTOALL",
    5: "FORWARD",
    9: "FORWARD",
    15: "FORWARD",
    18: "REPLAY",
    19: "REPLAYTOALL",
    20: "FORWARD"
};

const getStatusEmails = (status: string ) => {
    const statusEmails = dataToDisplay.participant
        .filter(email => email.status === 'TO')
        .map(email => email.email)
        .join(',')

        return statusEmails;
}

const sendRequiredData = () => {
    if(secondaryTabNumber !== null){
        const sendType = chooseSendMessageType[secondaryTabNumber]
        if(sendType !== undefined){
            switch (sendType) {
                case "REPLAY":
                    const firstParticipant = dataToDisplay.participant[0]
                    if(firstParticipant.status === "TO"){
                        dispatch(findUserOrClientEmailAction.setToInputValue({value: firstParticipant.email, valuType: ''}));
                    }else {
                        dispatch(findUserOrClientEmailAction.setCcInputValue({value: firstParticipant.email, valuType: ''}));
                    }
                    dispatch(emailCreatorAction.setOpenDialog(true));
                    dispatch(findUserOrClientEmailAction.setOpenToSearchBox(false));
                    dispatch(findUserOrClientEmailAction.setOpenCcSearchBox(false));
                    dispatch(sendEmailAction.setSubtitleValue(dataToDisplay.subtitle));
                    break;
                case "REPLAYTOALL":
                    const toEmails = getStatusEmails('TO');
                    const ccEmails =  getStatusEmails('CC');
                    
                    dispatch(findUserOrClientEmailAction.setToInputValue({value: toEmails, valuType: ''}));
                    dispatch(findUserOrClientEmailAction.setToInputValue({value: ccEmails, valuType: ''}));
                    dispatch(emailCreatorAction.setOpenDialog(true));
                    dispatch(findUserOrClientEmailAction.setOpenToSearchBox(false));
                    dispatch(findUserOrClientEmailAction.setOpenCcSearchBox(false));
                    dispatch(sendEmailAction.setSubtitleValue(dataToDisplay.subtitle));
                    break;
                case "FORWARD":
                    dispatch(emailCreatorAction.setOpenDialog(true));
                    dispatch(sendEmailAction.setSubtitleValue(dataToDisplay.subtitle));
                    break;    
                default: 
                    console.log("defoult value");
            }
        }

    }
}

useEffect(() => {
    sendRequiredData();
}, [secondaryTabNumber])

}

export default useSubFoldersEmailActions;