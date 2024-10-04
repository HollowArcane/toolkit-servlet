<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <link rel="stylesheet" href="/test/assets/css/chart.toolkit.css">
    <script src="/test/assets/js/chart.umd.js"></script>
    <script src="/test/assets/js/chart.toolkit.js"></script>

    <style>
        .grid {
            /* height: 500px; */
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            grid-template-rows: 600px 600px;

            gap: 2rem;
        }
    </style>

    <div class="grid">
        <chart type="doughnut">
            <labels>
                <label> Label 1 </label>
                <label> Label 2 </label>
                <label> Label 3 </label>
                <label> Label 4 </label>
                <label> Label 5 </label>
            </labels>

            <dataset name="My Dataset" background="linear-gradient(0, rgba(255, 0, 0, .3), rgba(0, 0, 255, .3))">
                <data img="assets/img/img-1.jpg"> 54 </data>
                <data> 35 </data>
                <data> 64 </data>
                <data> 24 </data>
                <data> 75 </data>
            </dataset>
            <dataset>
                <data> 35 </data>
                <data> 54 </data>
                <data> 65 </data>
                <data> 23 </data>
                <data> 53 </data>
            </dataset>
        </chart>
    </div>
</head>
<body>
    
</body>
</html>