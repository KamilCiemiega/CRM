import { Message } from "../../../../interfaces/interfaces";
import { AppDispatch } from "../../../store";
import { updateMessageFolder } from "../../../store/thunks/updateMessageFolder";

export const MoveMessageToDifferentFolder = async (
    secondaryTabNumber: number | null,
    clickedMessage: Message,
    dispatch: AppDispatch
) => {
    const foldersIndexes = [6, 7, 10, 11, 16, 21, 23];

    const statusMap: { [key: number]: { messageFolder?: number; restore?: boolean } } = {
        6: { messageFolder: 17 },
        7: { messageFolder: 22 },
        10: { messageFolder: 17 },
        11: { messageFolder: 22 },
        16: { messageFolder: 22 },
        21: { restore: true },
        23: { restore: true },
    };

    if (secondaryTabNumber === null || !foldersIndexes.includes(secondaryTabNumber)) {
        console.warn("Invalid secondaryTabNumber:", secondaryTabNumber);
        return;
    }

    const folderType = statusMap[secondaryTabNumber];

    const getRestoreFolder = (currentFolderName: string) => {
        return currentFolderName === "INBOX" ? { id: 1 } : { id: 8 };
    };

    const messageFolders =
        folderType.messageFolder !== undefined
            ? [...clickedMessage.messageFolders, { id: folderType.messageFolder }]
            : [...clickedMessage.messageFolders, getRestoreFolder(clickedMessage.messageFolders[0]?.name)];

    const updatedMessage = {
        ...clickedMessage,
        messageFolders,
    };

    try {
        await dispatch(updateMessageFolder(clickedMessage.id, updatedMessage));
        console.log("Message moved successfully to folder:", folderType.messageFolder || "restored");
    } catch (error) {
        console.error("Error moving message to a different folder:", error);
    }
};
