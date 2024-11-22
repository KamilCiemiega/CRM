import { deleteMessageFromFolder } from "../../../store/thunks/deleteMessageFromFolder";
import { AppDispatch } from "../../../store";
import { Message } from "../../../../interfaces/interfaces";

export const DeleteEmail = async (
    clickedMessage: Message, 
    secondaryTabNumber: number | null,
    dispatch: AppDispatch 
) => {
    const secondTabIndexes = [24, 25];

    if (secondaryTabNumber != null) {
        if (clickedMessage.messageFolders.length > 0 && secondTabIndexes.includes(secondaryTabNumber)) {
            console.log(secondaryTabNumber);
            try {
                if (secondaryTabNumber === 24) {
                    console.log("delete message " + secondaryTabNumber);
                    await dispatch(deleteMessageFromFolder({ folderId: 22, messageId: clickedMessage.id }));
                } else {
                    await dispatch(deleteMessageFromFolder({ folderId: 22 }));
                }
            } catch (err) {
                console.error("Error occurred while trying to remove message:", err);
            }
        }
    }
};
