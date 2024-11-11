import { Box } from "@mui/material";
import { styled } from "@mui/material/styles";

const StyledBox = styled(Box) ({
    height: '60px',
    background: 'white',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-start',
    marginTop: '14px',
    marginLeft: '2%'
}) as typeof Box;


export default StyledBox ;
