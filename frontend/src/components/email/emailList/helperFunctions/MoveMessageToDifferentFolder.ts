import { Message } from "../../../../interfaces/interfaces";
import { AppDispatch } from "../../../store";
import { updateMessageFolder } from "../../../store/thunks/updateMessageFolder";
import { deleteMessageFromFolder } from "../../../store/thunks/deleteMessageFromFolder";

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

    let folderType = statusMap[secondaryTabNumber];

    const getRestoreFolder = (currentFolderName: string) => {
        return currentFolderName === "NEW" ? { id: 1 } : { id: 8 };
    };

    const newFolderId =
        folderType.messageFolder !== undefined
            ? folderType.messageFolder
            : getRestoreFolder(clickedMessage.status).id;

    const updatedMessageFolders = clickedMessage.messageFolders.filter(
        (folder) => folder.id !== clickedMessage.messageFolders[0].id
    );

    const updatedMessage = {
        ...clickedMessage,
        messageFolders: [...updatedMessageFolders, { id: newFolderId }],
    };

    try {
        const folderToDelete = clickedMessage.messageFolders[0].id;
        await dispatch(deleteMessageFromFolder(folderToDelete, clickedMessage.id));
        await dispatch(updateMessageFolder(clickedMessage.id, updatedMessage));
    } catch (error) {
        console.error("Error moving message to a different folder:", error);
    }
};

