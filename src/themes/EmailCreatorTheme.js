import { createTheme } from '@mui/material/styles';


const EmailCreatorTheme = createTheme({
    components: {
      MuiDialog: {
        styleOverrides: {
          paper: {
            width: '100%',
            height: '85%',
            maxWidth: '1000px',
          },
        },
      },
      MuiDialogTitle: {
        styleOverrides: {
          root: {
            backgroundColor: '#f0f0f0',
            color: '#333',
            padding: '20px',
            display: "flex",
            alignItems: "center",
            justifyContent: "space-between"
          },
        },
      }
    },
  });

  export default EmailCreatorTheme;