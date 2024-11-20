import { useSelector, useDispatch } from "react-redux";
import { useEffect } from "react";
import { RootState } from "../../../store"; 
import { emailCreatorAction } from "../../../store/slices/emailSlices/emailCreator-slice"; 
import { findUserOrClientEmailAction } from "../../../store/slices/emailSlices/findUserOrClientEmail-slice";
import { sendEmailAction } from "../../../store/slices/emailSlices/sendEmail-slice"; 

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

const  useSubFoldersEmailActions = () => {
const dataToDisplay = useSelector((state: RootState) => state.emailPreview.dataToDisplay);
const secondaryTabNumber = useSelector((state: RootState) => state.emailList.secondaryTabNumber);
const dispatch = useDispatch();

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
                    dispatch(emailCreatorAction.setOpenDialog(true));
                    dispatch(sendEmailAction.setSubtitleValue(dataToDisplay.subtitle));                   
                    dispatch(findUserOrClientEmailAction.batchUpdate({
                        toInputValue: firstParticipant.status === "TO" 
                            ? firstParticipant.email: undefined,
                        ccInputValue: firstParticipant.status === "CC"
                            ? firstParticipant.email: undefined,
                        openToSearchBox: false,
                        openCcSearchBox: false,

                    }));
                    break;
                case "REPLAYTOALL":
                    const toEmails = getStatusEmails('TO');
                    const ccEmails =  getStatusEmails('CC');
                    dispatch(emailCreatorAction.setOpenDialog(true));
                    dispatch(sendEmailAction.setSubtitleValue(dataToDisplay.subtitle));
                    dispatch(findUserOrClientEmailAction.batchUpdate({
                        toInputValue: toEmails,
                        ccInputValue: ccEmails,
                        openToSearchBox: false,
                        openCcSearchBox: false,
                    }));
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