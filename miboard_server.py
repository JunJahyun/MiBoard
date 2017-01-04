from flask import Flask, request;
from flaskext.mysql import MySQL;
import json;

app = Flask(__name__);
mysql = MySQL();

app.config['MYSQL_DATABASE_USER'] = 'root';
app.config['MYSQL_DATABASE_PASSWORD'] = 'akekdk1';
app.config['MYSQL_DATABASE_DB'] = 'miboard';

mysql.init_app(app);

@app.route("/")
def helloworld():
	return "HelloWorld";

@app.route("/member", methods=["GET", "POST"])
def members():
	cursor = mysql.connect().cursor();
	cursor.execute("SELECT * FROM miboard_member")

	result = []
	columns = tuple( d[0] for d in cursor.description)

	for row in cursor:
		result.append(dict(zip(columns, row)))

	print(result);
	return json.dumps(result);

if __name__ == '__main__':
	app.run(debug=True, host="0.0.0.0", port=5009);