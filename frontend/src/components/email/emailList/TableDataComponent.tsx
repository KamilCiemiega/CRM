import { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { DataGrid, GridColDef, GridRowParams } from '@mui/x-data-grid';
import { RootState } from "../../store";
import { Rows } from "../../../interfaces/interfaces";
import { useParticipantsData } from "../../../hooks/useParticipantData";

export interface MessageRole {
  status: "TO" | "CC";
  participantId: number;
}

const TableDataComponent = () => {
    const filtredListOfMessages = useSelector((state: RootState) => state.emailList.filtredMessages);
    const [selectedMessageRoles, setSelectedMessageRoles] = useState<MessageRole[]>([]);

    const participantData = useParticipantsData(selectedMessageRoles);

    const columns: GridColDef[] = [
      { field: 'status', headerName: 'Status', flex: 1 },
      { field: 'subject', headerName: 'Subject', flex: 2 },
      { field: 'sendDate', headerName: 'Time', flex: 1 },
      { field: 'size', headerName: 'Size', flex: 1 },
      
    ];

    const formatDate = (timestamp: string) => {
      const date = new Date(timestamp).toISOString(); 
      const lastDotIntex = date.lastIndexOf('.');
      const trimmedString = lastDotIntex !== -1 ? date.slice(0, lastDotIntex) : date;

      return trimmedString;
    };

    const rowsFunction = (): Rows[] => {
      return filtredListOfMessages.length > 0
        ? filtredListOfMessages.map((m, i) => ({
            id: i,
            status: m.status,
            subject: m.subject,
            sendDate: formatDate(m.sentDate),
            size: m.size,
          }))
        : [];
    };

    const paginationModel = { page: 0, pageSize: 20 };

    const handleRowClick = (params: GridRowParams) => {
      
    const dataToDisplay =  {
      body: "",
      attachmentsNumber: 0,
      participant: []
    }

      const messageObject = filtredListOfMessages[params.row.id];
      const body = messageObject.body;
      const attachmentsNumber = messageObject.attachments.length;
      setSelectedMessageRoles(messageObject.messageRoles);
     
      console.log(participantData);
    };

    return (
        <DataGrid
        rows={rowsFunction()}
        columns={columns}
        initialState={{ pagination: { paginationModel } }}
        pageSizeOptions={[10, 20]}
        checkboxSelection
        onRowClick={handleRowClick}
        sx={{
          border: 0,
          "& .MuiDataGrid-row": {
            cursor: "pointer"
          }
        }}
      />
    );
}

export default TableDataComponent;