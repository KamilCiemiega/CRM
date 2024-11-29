import { createTheme } from "@mui/material";

const ListOfClientsTheme = createTheme({
    components: {
        MuiPaper: {
            styleOverrides: {
                root: {
                    backgroundColor: "#f3f3f3",
                    borderRadius: "12px",
                    padding: "16px",
                    boxShadow: "3px 4px 1px -1px rgba(0, 0, 0, 0.2)",
                    width: "300px",
                    height: "100px",
                    marginLeft: '15px',
                    marginTop: '30px',
                    display: "flex",
                    alignItems: "center",
                    transition: "all 0.3s ease",
                    "&:hover": {
                        backgroundColor: "#e0e0e0",
                        boxShadow: "5px 8px 15px rgba(0, 0, 0, 0.3)",
                        transform: "scale(1.05)",
                        cursor: 'pointer'
                    },
                },
            },
        },
    },
});

export default ListOfClientsTheme;