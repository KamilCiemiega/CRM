import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../../store";
import { useEffect, useState } from "react";
import { MoveMessageToDifferentFolder } from "../helperFunctions/MoveMessageToDifferentFolder";
import { DeleteEmail } from "../helperFunctions/DeleteEmail";

const useMoveMessageToFolder = () => {
    const dispatch = useDispatch<AppDispatch>();
    const [secondaryTabNumberIndexes, setSecondaryTabNumberIndexes] = useState<number>();
    const clickedMessage = useSelector((state :RootState) => state.emailPreview.clickedMessage);
    const secondaryTabNumber = useSelector((state: RootState) => state.emailList.secondaryTabNumber);

    const setTabIndexes = () => {
        const foldersIndexes = [6, 7, 10, 11, 16, 21, 23, 24, 25];
        if (secondaryTabNumber && foldersIndexes.includes(secondaryTabNumber)) {
            setSecondaryTabNumberIndexes(secondaryTabNumber);
        }
    };    

    useEffect(() => {
        setTabIndexes();
    }, [secondaryTabNumber])

    useEffect(() => {
        const moveMessage = async () => {
            await MoveMessageToDifferentFolder(secondaryTabNumber, clickedMessage, dispatch);
            await DeleteEmail(clickedMessage, secondaryTabNumber, dispatch)
        };
        moveMessage();

    }, [secondaryTabNumberIndexes, dispatch]); 
};


export default useMoveMessageToFolder;