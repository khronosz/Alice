//E.g.: Turns "backupManager" into "Backup Manager"
export const toCapitalize = (text) => {
    return text.replace(/(?:^|\.?)([A-Z])/g, function (x, y) {
        return " " + y.toLowerCase()
    }).replace(/^_/, "")
        .toLowerCase()
        .split(' ')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))
        .join(' ')
}