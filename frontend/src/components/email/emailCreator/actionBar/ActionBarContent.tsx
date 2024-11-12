import { Grid, Badge, Button } from "@mui/material";
import { AttachFile } from "@mui/icons-material";
import { useSelector } from "react-redux";
import { RootState } from "../../../store";
import EditTextBar from "./EditTextBar";

const ActionBarContent = ({
  handleSendData,
  onFileChange,
  uploadFileCounter
}: {
  handleSendData: () => void;
  onFileChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  uploadFileCounter: number;
}) => {
  const showMessagePreview = useSelector((state: RootState) => state.emailPreview.showMessagePreview);

  const attachFileSection = (
    <Grid item sx={{ml: showMessagePreview ? "0.5%" : ""}}>
      <input
        type="file"
        multiple
        id="fileInput"
        style={{ display: "none" }}
        accept=".pdf,.doc,.docx,.jpg,.png"
        onChange={onFileChange}
      />
      <Badge badgeContent={uploadFileCounter} color="success">
        <AttachFile
          sx={{ cursor: "pointer" }}
          onClick={() => document.getElementById("fileInput")!.click()}
        />
      </Badge>
    </Grid>
  );

  return showMessagePreview ? (
    attachFileSection
  ) : (
    <>
      <Grid item>
        <Button variant="contained" sx={{ mr: 1 }} onClick={handleSendData}>
          Send
        </Button>
      </Grid>
      <EditTextBar />
      {attachFileSection}
    </>
  );
};

export default ActionBarContent;
