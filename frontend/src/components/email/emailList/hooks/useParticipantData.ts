import { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { emailPreviewAction } from "../../../store/slices/emailSlices/emailPreview-slice";
import { RootState } from "../../../store";
import axios from "axios";

export interface ParticipantData {
  status: string;
  email: string;
}

const useParticipantsData = () => {
  const dispatch = useDispatch();
  const messageRoles = useSelector((state: RootState) => state.emailPreview.messageRoles);

  const [participantsData, setParticipantsData] = useState<ParticipantData[]>([]);
  const [loadingData, setLoadingData] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    if (messageRoles.length === 0) return;

    setLoadingData(true);

    const fetchParticipants = async () => {
      try {
        const data = await Promise.all(
          messageRoles.map(async (role) => {
            const response = await axios.get(
              `http://localdev:8082/api/message-participant/by-id?participantId=${role.participantId}`
            );
            return { status: role.status, email: response.data.email };
          })
        );
        setParticipantsData(data);
      } catch (error: unknown) {
        if (axios.isAxiosError(error)) {
          setError(error.message);
        } else if (error instanceof Error) {
          setError(error.message);
        }
      } finally {
        setLoadingData(false);
      }
    };

    fetchParticipants();

  }, [messageRoles]);

  useEffect(() => {
    if (participantsData.length > 0) {
      dispatch(emailPreviewAction.setDataToDisplay(participantsData));
    }
  }, [participantsData, dispatch]);

  return { participantsData, loadingData, error };
};

export default useParticipantsData;
