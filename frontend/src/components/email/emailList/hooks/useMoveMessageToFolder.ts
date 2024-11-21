import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../../store";
import { useEffect, useState } from "react";
import { MoveMessageToDifferentFolder } from "../helperFunctions/MoveMessageToDifferentFolder";

const useMoveMessageToFolder = () => {
    const dispatch = useDispatch<AppDispatch>();
    const [secondaryTabNumberIndexes, setSecondaryTabNumberIndexes] = useState<number>();
    const clickedMessage = useSelector((state :RootState) => state.emailPreview.clickedMessage);
    const secondaryTabNumber = useSelector((state: RootState) => state.emailList.secondaryTabNumber);

    const statusMap: { [key: number]: { messageFolder?: number; restore?: boolean } } = {                             
        6: { messageFolder: 17 },
        7: { messageFolder: 22 },
        10: { messageFolder: 17 },
        11: { messageFolder: 22 },
        16: { messageFolder: 22 },
        21: { restore: true },
        23: { restore: true },
    };

    const setTabIndexes = () => {
        const foldersIndexes = [6, 7, 10, 11, 16, 21, 23];
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
        };
        moveMessage();
    }, [secondaryTabNumberIndexes, dispatch]);
};


export default useMoveMessageToFolder;