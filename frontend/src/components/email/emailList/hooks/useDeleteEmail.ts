import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../../store";
import { useEffect } from "react";
import { MoveMessageToDifferentFolder } from "../helperFunctions/MoveMessageToDifferentFolder";

const useDeleteEmail = () => {
    const dispatch = useDispatch<AppDispatch>();
    const clickedMessage = useSelector((state :RootState) => state.emailPreview.clickedMessage);
    const secondaryTabNumber = useSelector((state: RootState) => state.emailList.secondaryTabNumber);

    useEffect(() => {
        const moveMessage = async () => {
            await MoveMessageToDifferentFolder(secondaryTabNumber, clickedMessage, dispatch);
        };
        moveMessage();
    }, [secondaryTabNumber, clickedMessage, dispatch]);
};


export default useDeleteEmail;