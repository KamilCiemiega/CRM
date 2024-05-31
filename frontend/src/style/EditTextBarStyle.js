import { Box } from "@mui/material";
import { styled } from "@mui/material/styles";

const StyledBox = styled(Box)(({ theme }) => ({
    height: '60px',
    background: 'white',
    position: 'absolute',
    top: '660px',
    left: '52px',
    boxShadow: '4px 16px 24px -5px rgba(66, 68, 90, 1)',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-start',
    padding: '8px',
    zIndex: '1'
}));


export default StyledBox ;
